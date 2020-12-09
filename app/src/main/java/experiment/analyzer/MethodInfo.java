package experiment.analyzer;

import java.util.HashMap;
import java.util.Map;

public class MethodInfo implements SyntaxInfo {
  final String name;
  final boolean isPublic;
  final String returnType;
  final String repository;
  final String fileName;

  public MethodInfo(String name, boolean isPublic, String returnType, String repository, String fileName) {
    this.name = name;
    this.isPublic = isPublic;
    this.returnType = returnType;
    this.repository = repository;
    this.fileName = fileName;
  }

  @Override
  public String toString() {
    return new StringBuilder()
      .append("name: ")
      .append(this.name)
      .append(", isPublic: ")
      .append(this.isPublic)
      .append(", returnType: ")
      .append(this.returnType)
      .append(", repository: ")
      .append(this.repository)
      .append(", fileName: ")
      .append(this.fileName)
      .toString();
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null) return false;
    if(obj == this) return true;
    if(obj instanceof MethodInfo) {
      return ((MethodInfo)obj).name.equals(this.name)
          && ((MethodInfo)obj).isPublic == this.isPublic
          && ((MethodInfo)obj).returnType.equals(this.returnType)
          && ((MethodInfo)obj).repository.equals(this.repository)
          && ((MethodInfo)obj).fileName.equals(this.fileName);
    }
    return false;
  }

  @Override
  public Map<String, String> toMap() {
    var map = new HashMap<String, String>();
    map.put("name", this.name);
    map.put("isPublic", this.isPublic ? "true" : "false");
    map.put("returnType", this.returnType);
    map.put("repository", this.repository);
    map.put("fileName", this.fileName);
    return map;
  }
}
