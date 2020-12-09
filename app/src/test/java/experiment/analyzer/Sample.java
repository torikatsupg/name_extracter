package experiment.analyzer;

public class Sample {
  // field
  final private String fieldText;
  Sample() {
    fieldText = "text";
  }

  public void visit() {
    String title = "title";
  }

  private String createText() {
    return "text";
  }
}
 
class Test {
  // field
  static final public boolean isTest = false;

  private String isTestDone() {
    return "true";
  }

  public void excecute() {
    // a
    final var a = 0;
    // b
    double b = 20.0;
    // method text
    String text = "text";
    System.out.println("print");
  }
}
