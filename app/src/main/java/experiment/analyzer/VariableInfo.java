package experiment.analyzer;

import java.util.HashMap;
import java.util.Map;

public class VariableInfo implements SyntaxInfo {
  final String name;
  final String type;
  final boolean isPublic;
  final boolean isFieldValue;
  final String repository;
  final String fileName;

  public VariableInfo(String name, boolean isPublic, String type, boolean isFieldValue, String repository, String fileName) {
    this.name = name;
    this.isPublic = isPublic;
    this.type = type;
    this.isFieldValue = isFieldValue;
    this.repository = repository;
    this.fileName = fileName;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null) return false;
    if(obj == this) return true;
    if(obj instanceof VariableInfo) {
      return ((VariableInfo)obj).name.equals(this.name)
          && ((VariableInfo)obj).isPublic == this.isPublic
          && ((VariableInfo)obj).type.equals(this.type)
          && ((VariableInfo)obj).isFieldValue == this.isFieldValue
          && ((VariableInfo)obj).repository.equals(this.repository)
          && ((VariableInfo)obj).fileName.equals(this.fileName);
    }
    return false;
  }

  @Override
  public String toString() {
    return new StringBuilder()
      .append("name: ")
      .append(this.name)
      .append(", isPublic: ")
      .append(this.isPublic)
      .append(", type: ")
      .append(this.type)
      .append(", isFieldValue: ")
      .append(this.isFieldValue)
      .append(", repository: ")
      .append(this.repository)
      .append(", fileName: ")
      .append(this.fileName)
      .toString();
  }

  @Override
  public Map<String, String> toMap() {
    var map = new HashMap<String, String>();
    map.put("name", this.name);
    map.put("type", this.type);
    map.put("isPublic", this.isPublic ? "true" : "false");
    map.put("isFieldValue", this.isFieldValue ? "true" : "false");
    map.put("repository", this.repository);
    map.put("fileName", this.fileName);
    return map;
  }
}
