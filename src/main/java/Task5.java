import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task5 implements Task {

    private final Path source;
    private final Path target;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread worker;
    public Task5(Path source, Path target) {
        this.source = source;
        this.target = target;
    }
    @Override
    public void start() {
        if (running.getAndSet(true)) {
            System.out.println("Синхронизация уже запущена.");
            return;
        }
        worker = new Thread(() -> {
            System.out.println("Синхронизация запущена: " + source + " -> " + target);
            while (running.get()) {
                try {
                    syncOnce();
                    Thread.sleep(3000); // проверка каждые 3 секунды
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (IOException e) {
                    System.err.println("Ошибка синхронизации: " + e.getMessage());
                }
            }
            System.out.println("Синхронизация остановлена.");
        }, "folder-sync");
        worker.setDaemon(true);
        worker.start();
    }

    @Override
    public void stop() {
        running.set(false);
        if (worker != null) worker.interrupt();
    }
    private void syncOnce() throws IOException {
        if (!Files.exists(source)) {
            System.err.println("Исходная папка не существует: " + source);
            return;
        }
        Files.createDirectories(target);
        Map<String, Long> targetFiles = collectFiles(target);
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(file);
                Path dest = target.resolve(relative);
                Long targetSize = targetFiles.get(relative.toString());
                boolean needCopy = !Files.exists(dest)
                        || targetSize == null
                        || targetSize != Files.size(file)
                        || Files.getLastModifiedTime(file).compareTo(Files.getLastModifiedTime(dest)) > 0;
                if (needCopy) {
                    Files.createDirectories(dest.getParent());
                    Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Синхронизирован: " + relative);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private Map<String, Long> collectFiles(Path root) throws IOException {
        Map<String, Long> result = new HashMap<>();
        if (!Files.exists(root)) return result;
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                result.put(root.relativize(file).toString(), attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        Path src = Paths.get("source_folder");
        Path dst = Paths.get("target_folder");
        Task5 sync = new Task5(src, dst);
        sync.start();
        Thread.sleep(15000); // работаем 15 секунд
        sync.stop();
    }
}