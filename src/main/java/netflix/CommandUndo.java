package netflix;


import util.TestUtil;

import java.util.*;


public class CommandUndo {

    Stack<Command> commandStack = new Stack<>();
    Map<String, Stack<Command>> tagToStack = new HashMap<>();

    public void execute(Command command, List<String> tags){
        commandStack.add(command);
        if (tags != null){
            for (String tag : tags){
                tagToStack.computeIfAbsent(tag,v->new Stack<>()).add(command);
            }
        }
        System.out.println("Executed command with name " + command.name  + " and tags : " + tags);
    }

    public Command undo(){
        return undo(null);
    }

    public Command undo(String tag){
        if(tag == null){
            return popValidCommand(commandStack);
        } else {
           Stack<Command> commandStackByTag =  tagToStack.get(tag);
           if(commandStackByTag != null){
               return popValidCommand(commandStackByTag);
           } else {
               return null;
           }
        }
    }

    private Command popValidCommand(Stack<Command> commandStackToPop) {
        while(!commandStackToPop.isEmpty() && commandStackToPop.peek().isUnDone()){
            Command unDoneCommand = commandStackToPop.pop(); // pop all undone commands
            System.out.println("Popped from tagStack , Command : " + unDoneCommand.name);
        }

        if(!commandStackToPop.isEmpty()){
            Command command = commandStackToPop.pop();
            command.undo();
            return command;
        }

        return null;
    }

    public static class Command {
        String name;
        boolean unDone = false;

        Command(String name){
            this.name = name;
        }

        public void undo(){
            System.out.println("Undoing command with name : " + name);
            unDone = true;
        }

        public boolean isUnDone(){
            return unDone;
        }
    }

    static void main() {
        CommandUndo commandUndo = new CommandUndo();
        LinkedHashMap<Command, List<String>> commands = new LinkedHashMap<>();
        commands.put(new Command("1"), null);
        commands.put(new Command("2"), List.of("happy", "sad", "angry"));
        commands.put(new Command("3"), List.of("funny", "sad"));
        commands.put(new Command("4"), List.of("happy"));

        for (Map.Entry<Command, List<String>> entry : commands.entrySet()){
            commandUndo.execute(entry.getKey(), entry.getValue());
        }


        TestUtil.check(commandUndo.undo().name.equals("4"));
        TestUtil.check(commandUndo.undo("happy").name.equals("2"));
        TestUtil.check(commandUndo.undo("sad").name.equals("3"));
        TestUtil.check(commandUndo.undo(null).name.equals("1"));

    }
}
