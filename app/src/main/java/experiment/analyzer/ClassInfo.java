package experiment.analyzer;

import java.util.HashMap;
import java.util.Map;

public class ClassInfo implements SyntaxInfo {
  final String name;
  final boolean isPublic;
  final String repository;
  final String fileName;

  public ClassInfo(String name, boolean isPublic, String repository, String fileName) {
    this.name = name;
    this.isPublic = isPublic;
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
    if(obj instanceof ClassInfo) {
      return ((ClassInfo)obj).name.equals(this.name)
          && ((ClassInfo)obj).isPublic == this.isPublic
          && ((ClassInfo)obj).repository.equals(this.repository)
          && ((ClassInfo)obj).fileName.equals(this.fileName);
    }
    return false;
  }

  @Override
  public Map<String, String> toMap() {
    var map = new HashMap<String, String>();
    map.put("name", this.name);
    map.put("isPublic", this.isPublic ? "true" : "false");
    map.put("repository", this.repository);
    map.put("fileName", this.fileName);
    return map;
  }
}
