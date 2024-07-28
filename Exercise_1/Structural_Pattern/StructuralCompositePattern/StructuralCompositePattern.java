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
