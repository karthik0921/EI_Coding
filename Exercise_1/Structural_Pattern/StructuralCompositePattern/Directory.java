import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemComponent {
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
        showComponentsDetails(components);
    }

    private void showComponentsDetails(List<FileSystemComponent> components) {
        for (FileSystemComponent component : components) {
            component.showDetails();
        }
    }
}
