package Operators;

import VarTypes.DigitVar;
import VarTypes.StringVar;
import VarTypes.VarType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class OpUseObject extends Operator {

    public OpUseObject(String useOperation) {
        super(useOperation);
    }

    @Override
    public void exec(Interpretater inte) {
        String cmd[] = code.split("\\.");
        String objName = cmd[0];
        String operation = cmd[1];
        ObjectContainer obj = inte.getObj(objName);

        if(obj.hasVar(operation)) {
            inte.put(code.trim(), obj.getVar(operation));
        }
        else if(obj.hasMethod(operation)) {
            ArrayList<String> method = obj.getMethod(operation);
            execMethod(inte.getVars(), method);
        }
        else {
            System.err.println("No such an object field or method found. (" + operation + ")");
        }
    }

    private void execMethod(HashMap<String, VarType> vars, ArrayList<String> lines) {

        lines.remove(0);
        lines.remove(lines.size()-1);

        Interpretater innerInte = new Interpretater();
        innerInte.setVars(vars);
        Iterator<String> iter = lines.iterator();

        while(iter.hasNext()) {
            String line = iter.next();
            if (line == null) break;

            if (!line.contains("{") && !line.contains("[")) {
                innerInte.parse(line.trim());
            }else if (line.contains("{")){
                ArrayList<String> innerLines = new ArrayList<>();
                try {
                    do {
                        if (line == null) {
                            break;
                        }
                        innerLines.add(line);
                        line = iter.next();
                    } while (!line.contains("}"));
                    innerLines.add(line);
                    innerInte.parse(innerLines);
                }catch (NullPointerException e) {
                    System.err.println("Error: missing '}'.");
                }
            }else if(line.contains("[")) {
                ArrayList<String> innerLines = new ArrayList<>();
                try {
                    do {
                        if (line == null) {
                            break;
                        }
                        innerLines.add(line);
                        line = iter.next();
                    } while (!line.contains("]"));
                    innerLines.add(line);
                    innerInte.parse(innerLines);
                }catch (NullPointerException e) {
                    System.err.println("Error: missing ']'.");
                }
            }
        }
        innerInte.run();
    }


}
