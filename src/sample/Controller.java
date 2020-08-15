package sample;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    HashMap<String,vertex> vmap=new HashMap<>();
    HashMap<String, ArrayList<edge>> graph=new HashMap<>();
    String s1="",s2="";
    @FXML
    AnchorPane g1;
    @FXML
    TextField t1,t2,t3;
    @FXML
    Button b1;
    @FXML
    void addVertex(ActionEvent E) {
        try {
            String vn = t1.getText();
            int x = Integer.parseInt(t2.getText());
            int y = Integer.parseInt(t3.getText());
            s1=vn;
            vertex v = new vertex(vn, x, y);
            if (!vmap.containsKey(vn)) {
                graph.put(vn, new ArrayList<edge>());
            }
            else throw new Exception1("Vertex "+vn+" Already Exist");

            vmap.put(vn, v);
            tr.setText("Vertex "+vn+" Added");

            printGraph();
        }
        catch (Exception e)
        {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            if(e.getMessage().length()==0)
            al.setHeaderText("Invalid Entries for Vertex");
            else al.setHeaderText(e.getMessage());
            al.setContentText("Please Enter Correct Detail");
            al.showAndWait();
        }

    }
    @FXML
    void printGraph()
    {
        g1.getChildren().clear();
        Circle c=null;
        for(vertex v:vmap.values())
        {
            int x=v.x;
            int y=v.y;

            c=new Circle(x*4,y*4,10);
            c.setFill(Color.YELLOW);
            c.setStroke(Color.BLACK);
            g1.getChildren().add(c);
            Text t=new Text((x+3)*4,(y+3)*4,v.name);
            t.setFill(Color.GREENYELLOW);
            g1.getChildren().add(t);
        }

        for(ArrayList<edge> ls:graph.values())
        {
            for(edge r1 : ls)
            {
                vertex v1,v2;
                v1=vmap.get(r1.from);
                v2=vmap.get(r1.to);
                Line l1=new Line(v1.x*4,v1.y*4,v2.x*4,v2.y*4);

                Text t=new Text((v1.x*4+v2.x*4)/2,(v1.y*4+v2.y*4)/2,String.valueOf(r1.weight));
                l1.setStroke(Color.GREENYELLOW);
                g1.getChildren().add(l1);
                t.setFill(Color.WHITE);
                g1.getChildren().add(t);

            }
        }
    }
    @FXML
    void modifyVertex(ActionEvent E) {
        try {
            String vn = t1.getText();
            int x = Integer.parseInt(t2.getText());
            int y = Integer.parseInt(t3.getText());

            if(!vmap.containsKey(vn)) throw new Exception1("Vertex "+vn+" Doesn't Exists");
            else {
                vertex v = vmap.get(vn);
                v.x=x;
                v.y=y;
                vmap.put(vn, v);
            }
            tr.setText("Vertex "+vn+" Modified ");
            printGraph();
        }
        catch (Exception  e)
        {
            Alert al=new Alert(Alert.AlertType.WARNING);
            al.setTitle("Warning !!");
            if(e.getMessage().length()==0)
                al.setHeaderText("Invalid Entries for Vertex");
            else al.setHeaderText(e.getMessage());
            al.setContentText("Please Enter Correct Detail");
            al.showAndWait();
        }
    }
    @FXML
    TextField t4;
    @FXML
    TextArea tr;
    @FXML
    void searchVertex(ActionEvent E){
        String vn=t4.getText();
        vertex v1;

        try {
            if (vmap.get(vn) != null) {
                v1 = vmap.get(vn);
                tr.setText("Vertex name: " + v1.name + "\nX: " + v1.x + "\nY " + v1.y);

            }
            else throw new Exception1("Vertex "+vn+" Doesn't Exists");
        }
        catch (Exception e)
        {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            al.setHeaderText("Vertex " + vn + " doesn't exists");
            al.setContentText("Please Enter Correct Detail");
            al.showAndWait();
        }

    }
    @FXML
    void deleteVertex(ActionEvent E) {
        String vn = t4.getText();
        try {
            if (vmap.get(vn) != null) {
               // vertex v1 = vmap.get(vn);
                vmap.remove(vn);
                graph.remove(vn);
                for (ArrayList<edge> list : graph.values()) {
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).to.equals(vn))
                            list.remove(i);
                    }
                }
                tr.setText("Vertex " + vn + " deleted successfully");
                printGraph();
            }
            else throw new Exception();
        }
        catch (Exception e) {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            al.setHeaderText("Vertex " + vn + " doesn't exists");
            al.setContentText("Please Enter Correct Vertex");
            al.showAndWait();
        }
    }

    @FXML
    TextField e1,e2,e3,e4,e5;

    @FXML
    Button b2;
    @FXML
    void addEdge(ActionEvent E){
        try {
            String vf = e1.getText();
            String vt = e2.getText();
            int wgt = Integer.parseInt(e3.getText());
            if(!vmap.containsKey(vf) || !vmap.containsKey(vt)) throw new Exception1("Vertices Not Found !!");

            edge e = new edge(vf, vt, wgt);
            if (graph.get(vf) == null)
                  graph.put(vf, new ArrayList<edge>());
            ArrayList<edge> ls = graph.get(vf);
            ls.add(e);
            graph.put(vf, ls);
            tr.setText("Edge Added Successfully From " + vf + " To " + vt);
            printGraph();
        }
        catch (Exception e){
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            if(e.getMessage().length()==0)
                al.setHeaderText("Invalid Entries for Edge");
            else al.setHeaderText(e.getMessage());
            al.setContentText("Please Enter Correct Detail");
            al.showAndWait();
        }
    }

    @FXML
    void modifyEdge(ActionEvent E){
        try {
            String vf = e1.getText();
            String vt = e2.getText();
            int wgt = Integer.parseInt(e3.getText());
            if(!vmap.containsKey(vf) || !vmap.containsKey(vt)) throw new Exception();

            ArrayList<edge> ls = graph.get(vf);

            int flag=0;
            for (edge e : ls) {
                if (e.to.equals(vt)) {
                    e.weight = wgt;
                    flag=1;
                    break;
                }
            }
            printGraph();
            if(flag==1)
                tr.setText("Edge Modified Successfully From " + vf + " To " + vt);
            else throw new Exception1("Edge Not Found");
        }
        catch (Exception e)
        {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            if(e.getMessage().length()==0)
                al.setHeaderText("Invalid Entries for Edge");
            else al.setHeaderText(e.getMessage());
            al.setContentText("Please Enter Correct Detail");
            al.showAndWait();

        }
    }

    @FXML
    void searchEdge(ActionEvent E){
        int flag=0;
        try {
            String vf = e4.getText();
            String vt = e5.getText();
            //int wgt=Integer.parseInt(e3.getText());
            ArrayList<edge> ls = graph.get(vf);
            if(!vmap.containsKey(vf) || !vmap.containsKey(vt)) throw new Exception1("Vertices Not Found ");

            // int flag = 0;
            for (edge e : ls) {
                if (e.to.equals(vt)) {
                    tr.setText("From Vertex : " + e.from + "\nTo Vertex : " + e.to + "\nWeight : " + e.weight);
                    flag = 1;
                    break;
                }
            }

            if(flag==0) throw  new Exception1("Edge doesn't exist from "+vf+" to "+vt);
                //tr.setText("Edge doesn't exists from "+vf+" to "+vt);

        }
        catch (Exception e){
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            if(e.getMessage().length()==0)
                al.setHeaderText("Invalid Entries for Edge");
            else al.setHeaderText(e.getMessage());
            al.setContentText("Please Enter Correct Detail");
            al.showAndWait();

        }

    }
    @FXML
    void deleteEdge(ActionEvent E){
        try {
            String vf = e4.getText();
            String vt = e5.getText();
            //int wgt=Integer.parseInt(e3.getText());
            if(!vmap.containsKey(vf) || !vmap.containsKey(vt)) throw new Exception1("bb");

            ArrayList<edge> ls = graph.get(vf);
            int flag = 0;
            for (edge e : ls) {
                if (e.to.equals(vt)) {
                    ls.remove(e);
                    tr.setText("Edge deleted from " + e.from + " to " + e.to);
                    flag = 1;
                    break;
                }
            }
            if(flag==0) throw new Exception();
            else printGraph();
        }
        catch (Exception e)
        {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            al.setHeaderText("Edge not found");
            al.setContentText("Please Enter Correct Detail");
            al.showAndWait();

        }
        //if(flag==0)
          //  tr.setText("Edge doesn't exists from "+vf+" to "+vt);

    }


    @FXML
    TextField i1;
    @FXML
    void importGraph(ActionEvent E) throws FileNotFoundException {
        try {
            String path = i1.getText();
            File f = new File(path);
            Scanner sc = new Scanner(f);
            try{
                int v = sc.nextInt();
                for (int i = 0; i < v; i++) {
                    String vn = sc.next();
                    int x = sc.nextInt();
                    int y = sc.nextInt();


                    t1.setText(vn);
                    t2.setText(String.valueOf(x));
                    t3.setText(String.valueOf(y));
                    b1.fire();

                   // vertex v1 = new vertex(vn, x, y);
                  //  Circle c1=new Circle(x,y,10);
                    //c1.getFill();
                   // g1.getChildren().add(c1);
//                    if (!vmap.containsKey(vn))
//                        graph.put(vn, new ArrayList<>());
//
//                    vmap.put(vn, v1);
                }
//                System.out.println("v success");
                int edges = sc.nextInt();
                for (int i = 0; i < edges; i++) {
                    String from = sc.next();
                    String to = sc.next();
                    int wgt = sc.nextInt();

                    if(!vmap.containsKey(from)||!vmap.containsKey(to)) throw new Exception();
                    e1.setText(from);
                    e2.setText(to);
                    e3.setText(String.valueOf(wgt));
                    b2.fire();


                    //edge eg = new edge(from, to, wgt);
                    /*if (!vmap.containsKey(from)) {
                        vertex v1 = new vertex(from, 0, 0);
                        graph.put(from, new ArrayList<>());
                        vmap.put(from, v1);
                    }
                    if (!vmap.containsKey(to)) {
                        vertex v1 = new vertex(to, 0, 0);
                        graph.put(to, new ArrayList<>());
                        vmap.put(to, v1);
                    }



                   if (graph.get(from) != null) {
                        ArrayList<edge> ls = graph.get(from);
                        ls.add(eg);
                        graph.put(from, ls);
                    } else {
                        ArrayList<edge> ls = new ArrayList<>();
                        ls.add(eg);
                        graph.put(from, ls);
                    }

                    System.out.println("e");

                    */
                }
                sc.close();
                tr.setText("Graph Imported Successfully");
            } catch (Exception e) {
                Alert al=new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Information !!");
                al.setHeaderText("Wrong Inputs");
                al.setContentText("Please Check Input File");
                al.showAndWait();

            }

        }
        catch (Exception e)
        {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            al.setHeaderText("File not found");
            al.setContentText("Please Enter Correct Path");
            al.showAndWait();
        }
    }

    @FXML
    TextField exp;
    @FXML
    void exportGraph(ActionEvent E) throws IOException {
        try {
            String path = exp.getText();
            File f = new File(path);
            FileWriter sc = new FileWriter(f);

            for (ArrayList<edge> rd : graph.values()) {
                Collections.sort(rd, new Comparator1());
            }

            int ne=0;
            sc.write(graph.keySet().size()+"\n");
            for(String s:graph.keySet())
            {
                vertex v=vmap.get(s);
                sc.write(v.name+" "+v.x+" "+v.y+"\n");
                ne+=graph.get(s).size();
            }
            sc.write(ne+"\n");
            for (ArrayList<edge> rd : graph.values()) {
                for (edge r1 : rd)
                    sc.write(r1.toString() + "\n");
            }
            sc.close();
            tr.setText("Graph Exported Successfully");
        }
        catch(Exception e)
        {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error !!");
            al.setHeaderText("File not found");
            al.setContentText("Please Enter Correct Path");
            al.showAndWait();
        }

    }

    @FXML
    TextField d1,d2;
    @FXML
    void DiksPath(ActionEvent E)
    {
        try {
            String from = d1.getText();
            String to = d2.getText();

            if(!vmap.containsKey(from)|| !vmap.containsKey(to)) throw new Exception();
            ArrayList<edge> path = Dijkstra.diks(graph, vmap, from, to);
            if (path == null) {
                tr.setText("No Path Exists Between " + from + " and " + to);
            } else {
                String way = from;
                for (edge e : path) {
                    way += " -> " + e.to;
                    if (e.to.equals(to)) break;
                }


                tr.setText("The Shortest Path is :\n" + way);
            }
            showPath(path);
        }
        catch (Exception e)
        {
            Alert al=new Alert(Alert.AlertType.WARNING);
            al.setTitle("Warning !!!");
            al.setHeaderText("");
            al.setContentText("Please Enter Correct Vertices");
            al.showAndWait();

        }
    }

    @FXML
    TextField shapeTF;
    void showPath(ArrayList<edge> path1){

        Path path=new Path();
        edge e1=path1.get(0);
        vertex v1=vmap.get(e1.from);
        String shape=shapeTF.getText();
        PathTransition pt=new PathTransition();
        PathTransition pt1=new PathTransition();
        Shape s1,s2;
        switch (shape){
            case "Circle":
                s1=new Circle(v1.x*4,v1.y*4,5);
                s1.setFill(Color.PINK);
                s1.setStrokeWidth(20);
                g1.getChildren().add(s1);
                pt.setNode(s1);
                break;
            case "Square":
                s1=new Rectangle(0,0,10,10);
                s1.setFill(Color.PINK);
                s1.setStrokeWidth(20);
                g1.getChildren().add(s1);
                pt.setNode(s1);
                break;
            case "Triangle":
                Polygon p1=new Polygon();
                p1.getPoints().addAll(new Double[]{0.0,0.0,20.0,0.0,10.0,10.0});
                p1.setFill(Color.PINK);
                p1.setStrokeWidth(10);
                g1.getChildren().add(p1);
                pt.setNode(p1);
                break;

            case "Plus":
                s1=new Line(5.0,10.0,15.0,10.0);
                s2=new Line(10.0,5.0,10.0,15.0);
                s1.setStroke(Color.PINK);
                s2.setStroke(Color.PINK);
                s1.setStrokeWidth(5);
                s2.setStrokeWidth(5);
                g1.getChildren().add(s1);
                g1.getChildren().add(s2);
                pt.setNode(s1);
                pt1.setNode(s2);
                break;

            case "Cross":
                s1=new Line(5.0,5.0,15.0,15.0);
                s2=new Line(5.0,15.0,15.0,5.0);
                s1.setStroke(Color.PINK);
                s2.setStroke(Color.PINK);
                s1.setStrokeWidth(5);
                s2.setStrokeWidth(5);
                g1.getChildren().add(s1);
                g1.getChildren().add(s2);
                pt.setNode(s1);
                pt1.setNode(s2);
                break;
        }

        MoveTo mt=new MoveTo(v1.x*4,v1.y*4);
        path.getElements().add(mt);

        for(edge e:path1)
        {
            vertex v=vmap.get(e.to);
            LineTo lt=new LineTo(v.x*4,v.y*4);
            path.getElements().add(lt);
        }

        pt.setDuration(Duration.millis(2500));
        pt.setPath(path);
        pt.setCycleCount(25);
        pt.setAutoReverse(false);
        if(shape.equals("Plus")||shape.equals("Cross"))
        {
            pt1.setDuration(Duration.millis(2500));
            pt1.setPath(path);
            pt1.setCycleCount(25);
            pt1.setAutoReverse(false);
            pt1.play();
        }
        pt.play();

    }
    @FXML
    void createVertex(MouseEvent E)
    {
        t2.setText(String.valueOf((int)E.getX()/4));
        t3.setText(String.valueOf((int)E.getY()/4));
        TextInputDialog d=new TextInputDialog();
        d.setContentText("Enter New Vertex Name");
        Optional<String> result=d.showAndWait();
        if(result.isPresent())
        {
            t1.setText(result.get());
            b1.fire();
        }
    }


}

class Exception1 extends Exception{
    public Exception1(String s)
    {
        super(s);
    }
}
class Dijkstra{
    static ArrayList<edge> diks(HashMap<String, ArrayList<edge>> graph, HashMap<String,vertex>vmap , String v00, String vf) {
        HashMap<String, Double> dist = new HashMap<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        HashMap<String, ArrayList<edge>> parent = new HashMap<>();

        double d1, d2;
        vertex v0=vmap.get(v00);
        v0.cost=0.0;
        for (String v1 : graph.keySet()) {
            dist.put(v1, Double.MAX_VALUE);
            visited.put(v1, false);
            parent.put(v1, null);
        }
        dist.put(v0.name, 0.0);
        parent.put(v0.name, null);

        PriorityQueue<vertex> pq = new PriorityQueue<>(new Comparator2());
        pq.add(v0);

        while (pq.size()>0) {
            vertex p = pq.remove();
            if (visited.get(p.name)) continue;
            else {
                visited.put(p.name, true);
                ArrayList<edge> temp = graph.get(p.name);
                for (edge r1 : temp) {
                    d1 = dist.get(r1.to);
                    d2 = dist.get(p.name) + r1.weight;

                    if(d2<d1)
                    {
                        dist.put(r1.to,d2);
                        ArrayList<edge> p2 = parent.get(r1.from);
                        ArrayList<edge> p3=new ArrayList<>();
                        if (p2 == null) {
                            p3.add(r1);
                        } else {
                            for(edge r3:p2)
                                p3.add(r3);
                            p3.add(r1);
                        }
                        parent.put(r1.to,p3);
                        vertex v2=vmap.get(r1.to);

                        v2.cost=d2;
                        pq.add(v2);
                    }

                }
            }
        }

       return parent.get(vf);
    }

}

class Comparator1 implements Comparator<edge>{
    public int compare(edge e1,edge e2)
    {
        if(e1.to.equals(e2.to))
        {
            if(e1.weight<e2.weight) return -1;
            else return 1;
        }
        else{
            return e1.to.compareTo(e2.to);
        }
    }
}

class Comparator2 implements Comparator<vertex>{
    public int compare(vertex e1,vertex e2)
    {
        if(e1.cost<e2.cost) return -1;
        if(e1.cost==e2.cost) return 0;
        if(e1.cost>e2.cost) return  1;
        return 1;
    }
}
class vertex {
    String name;
    int x;
    int y;
    Double cost;
    vertex(String nm,int x,int y)
    {
        name=nm;
        this.x=x;
        this.y=y;
    }
}

class edge{
    String from;
    String to;
    int weight;
    edge(String fr,String vt,int wt)
    {
        from=fr;
        to=vt;
        weight=wt;
    }

    public String toString()
    {
        return (from+" "+to+" "+weight);
    }
}
