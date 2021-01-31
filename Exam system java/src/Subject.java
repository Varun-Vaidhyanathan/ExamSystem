public class Subject {

    private String subjectName;

    public Subject(){
        subjectName = "";
    }

    public Subject(String name){
        this.subjectName = name;
    }

    public String getSubjectName(){
        return subjectName;
    }

    public void setSubjectName(String nameOfSubject){
        this.subjectName = nameOfSubject;
    }


    public static boolean hasSubject(Logger logger, Subject subject)throws InvalidLoggerException{

        if(logger.getID() == 0 || logger.getName().equals("")){
            throw new InvalidLoggerException("The account you are trying to access does not exist");
        }

        for(int i = 0; i < logger.getSubjects().size(); i++) {
            if (subject.getSubjectName().equalsIgnoreCase(logger.getSubjects().get(i).getSubjectName())){
                return true;
            }
        }
        return false;

    }

}
