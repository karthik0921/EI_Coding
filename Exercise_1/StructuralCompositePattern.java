import java.util.ArrayList;
import java.util.List;

abstract class FileSystemComponent {
    public abstract void showDetails();
}

class File extends FileSystemComponent {
    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void showDetails() {
        System.out.println("File: " + name);
    }
}

class Directory extends FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }

    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void showDetails() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component : components) {
            component.showDetails();
        }
    }
}


public class StructuralCompositePattern {
    public static void main(String[] args) {
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");

        Directory directory1 = new Directory("Directory1");
        directory1.addComponent(file1);

        Directory directory2 = new Directory("Directory2");
        directory2.addComponent(file2);
        directory2.addComponent(directory1);

        directory2.showDetails();
    }
}
