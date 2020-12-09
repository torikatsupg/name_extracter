package experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import experiment.analyzer.*;

public class Analyzer {
  final String repository;
  final String fileName;

  public Analyzer(String repository, String fileName) {
    this.repository = repository;
    this.fileName = fileName;
  }

  // クラス一覧を取得
  public ArrayList<ClassInfo> extractClass(File file) throws FileNotFoundException {
    var cu = parser(file);
    var list = new ArrayList<ClassInfo>();
    cu.accept(new ClassVisitor(this.repository, this.fileName), list);
    return list;
  }

  // メソッド一覧を取得
  public ArrayList<MethodInfo> extractMethod(File file) throws FileNotFoundException {
    var cu = parser(file);
    var list = new ArrayList<MethodInfo>();
    cu.accept(new MethodVisitor(this.repository, this.fileName), list);
    return list;
  }

  // 変数一覧を取得
  public ArrayList<VariableInfo> extractVariable(File file) throws FileNotFoundException {
    var cu = parser(file);
    var list = new ArrayList<VariableInfo>();
    cu.accept(new VariableVisitor(this.repository, this.fileName), list);
    return list;
  }

  // ソースコード解析
  private CompilationUnit parser(File file) throws FileNotFoundException {
    return new JavaParser().parse(file).getResult().get();
  }
}

// クラス用visitor
final class ClassVisitor extends VoidVisitorAdapter<ArrayList<ClassInfo>> {
  final String repository;
  final String fileName;
  public ClassVisitor(String repository, String fileName) {
    super();
    this.repository = repository;
    this.fileName = fileName;
  }

  @Override
  public void visit(ClassOrInterfaceDeclaration n, ArrayList<ClassInfo> arg) {
    final var info = new ClassInfo(
        n.getNameAsString(),
        VisitorUtil.hasPublicModifier(n.getModifiers()),
        this.repository,
        this.fileName
    );
    arg.add(info);
    super.visit(n, arg);
  }
}

// メソッド用Visitor
final class MethodVisitor extends VoidVisitorAdapter<ArrayList<MethodInfo>> {
  final String repository;
  final String fileName;
  public MethodVisitor(String repository, String fileName) {
    super();
    this.repository = repository;
    this.fileName = fileName;
  }

  @Override
  public void visit(MethodDeclaration n, ArrayList<MethodInfo> arg) {
    final var info = new MethodInfo(
        n.getNameAsString(),
        VisitorUtil.hasPublicModifier(n.getModifiers()),
        n.getTypeAsString(),
        this.repository,
        this.fileName
    );
    arg.add(info);
    super.visit(n, arg);
  }
}

// 変数用のVisitor
final class VariableVisitor extends VoidVisitorAdapter<ArrayList<VariableInfo>> {
  final String repository;
  final String fileName;
  public VariableVisitor(String repository, String fileName) {
    super();
    this.repository = repository;
    this.fileName = fileName;
  }

  // 変数を抽出
  @Override
  public void visit(VariableDeclarationExpr n, ArrayList<VariableInfo> arg) {
    final var info = new VariableInfo(
        n.getVariable(0).toString().split(" ")[0],
        VisitorUtil.hasPublicModifier(n.getModifiers()),
        n.getElementType().toString(),
        false,
        this.repository,
        this.fileName
    );
    arg.add(info);
    super.visit(n, arg);
  }

  // フィールドを抽出
  @Override
  public void visit(FieldDeclaration n, ArrayList<VariableInfo> arg) {
    final var info = new VariableInfo(
        n.getVariable(0).toString().split(" ")[0],
        VisitorUtil.hasPublicModifier(n.getModifiers()),
        n.getElementType().toString(),
        true,
        this.repository,
        this.fileName
    );
    arg.add(info);
    super.visit(n, arg);
  }
}

final class VisitorUtil {
  // publicかprivateか確認する
  static public boolean hasPublicModifier(NodeList<Modifier> nodeList) {
    return nodeList.contains(Modifier.publicModifier());
  }
}
