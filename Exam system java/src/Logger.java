import java.util.*;
public class Logger {

    private String name;
    private ArrayList<Subject> subjects;
    private boolean isStudent;
    private boolean isTeacher;
    private boolean isExamSetter;
    private long ID;
    private LinkedList<Test> tests = new LinkedList<Test>();
    private static LinkedList<Test> allTestsEver = new LinkedList<Test>();
    private LinkedList<Test> solvedTests = new LinkedList<Test>();
    private HashMap<Test, Double> gradedTests = new HashMap<Test,Double>();
    private HashMap<Test,ArrayList> studentAnswers = new HashMap<>();//this will save the test and
    //respective test's answers--> the last element will be the explaining answer object.

    private static ArrayList<Logger> allLoggers;


    public Logger(){
        name = "";
        subjects = new ArrayList<>();
        isStudent = false;
        isTeacher = false;
        isExamSetter = false;
        ID = 0;
    }

    public LinkedList<Test> getSolvedTests() {
        return solvedTests;
    }

    public HashMap<Test, ArrayList> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(HashMap<Test, ArrayList> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    public static void setAllLoggers(ArrayList<Logger> allLoggers) {
        Logger.allLoggers = allLoggers;
    }

    public long getID() {
        return ID;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Subject> getSubjects(){
        return subjects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public boolean isExamSetter() {
        return isExamSetter;
    }

    public void setExamSetter(boolean isExamSetter) {
        this.isExamSetter = isExamSetter;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public LinkedList<Test> getTests() {
        return tests;
    }

    public void setTests(LinkedList<Test> tests) {
        this.tests = tests;
    }

    public static ArrayList<Logger> getAllLoggers() {
        return allLoggers;
    }

    //
    //make this method return an arraylist of available tests for easier access of tests in driver class
    //
    public ArrayList showAvailableTests()throws InvalidLoggerException{
        if(!this.isStudent())throw new InvalidLoggerException("Only Students have available tests");
        String out = "";
        ArrayList<Test> availableTests = new ArrayList<>();
        //now we need to iterate through the tests linked list
        for(int i=0;i<this.getTests().size();i++){
            if(Subject.hasSubject(this,this.getTests().get(i).getTestSubject())){
                //if the student has that subject, the test becomes available
                // hence we need to add it to String out
                out+= (i+1)+". Test ID: "+this.getTests().get(i).getTestID()+"\n Test Name: "+
                        this.getTests().get(i).getTestName()+"\n Test Subject: "+
                        this.getTests().get(i).getTestSubject()+"\n";
                availableTests.add(this.getTests().get(i));
            }
        }
        this.printTheString(out);
        return availableTests;
    }

    public LinkedList showSolvedTests()throws InvalidLoggerException{
        if(!this.isStudent())throw new InvalidLoggerException("Only Students will have solved tests");
        String out="";
        LinkedList<Test> solvedTestsList = this.getSolvedTests();
        //now we need to iterate through solved tests linked list
        for(int i=0; i<this.getSolvedTests().size();i++){

            out+= (i+1)+". Test ID: "+this.getTests().get(i).getTestID()+"\n Test Name: "+
                    this.getTests().get(i).getTestName()+"\n Test Subject: "+
                    this.getTests().get(i).getTestSubject()+"\n";

        }
        this.printTheString(out);
        return solvedTestsList;
    }

    public HashMap<Test, Double> getGradedTests() {
        return gradedTests;
    }

    public HashMap showGradedTests()throws InvalidLoggerException{
        if(!(this.isStudent() || this.isTeacher())) throw new InvalidLoggerException("Only students and teachers will" +
                "have graded tests");

        String out="";
        int i=0;
        for(Map.Entry<Test,Double> entry: this.getGradedTests().entrySet()){
            out+= (i+1)+". Test ID: "+entry.getKey().getTestID()+"\n Test Name: "+
                    entry.getKey().getTestName()+"\n Test Subject: "+
                    entry.getKey().getTestSubject()+"\n Score: "+entry.getValue()+"\n";
            i++;
        }
        this.printTheString(out);
        return this.getGradedTests();
    }

    public void addTest(Test newTest)throws InvalidTestException{

        if(newTest.getTestID() == 0){
            throw new InvalidTestException("The test you are trying to add is invalid");
        }

        for(int i=0; i< allTestsEver.size(); i++){

            if (allTestsEver.get(i).getTestID() == newTest.getTestID()){
                throw new InvalidTestException("A test with the same ID already exists");
            }

        }

        //here the test will be valid

        allTestsEver.add(newTest);// adding tests into list of all the tests ever added

        for(int i =0 ; i< allLoggers.size(); i++){
            if(allLoggers.get(i).isStudent() && allLoggers.get(i).getSubjects().contains(newTest.getTestSubject())){
                allLoggers.get(i).getTests().add(newTest);//if the logger is a student, we add the test
            }
        }

    }

    //needs to be called by a Logger object(Student)
    public void addSolvedTest(Test solvedTest)throws InvalidTestException{
        if(solvedTest.getTestID() == 0){
            throw new InvalidTestException("This test does not exist");
        }
        if(this.isStudent()) {
            this.solvedTests.add(solvedTest);
            this.tests.remove(solvedTest);//removing the solved tests from the available tests for the student.
        }
    }


    //Needs to be called by a logger teacher and student separately so that the student can view graded tests
    public void addGradedTests(Test gradedTest, double score) throws InvalidTestException{
        if(gradedTest.getTestID() == 0)
            throw new InvalidTestException("The graded test does not exist");

        if(this.isStudent() || this.isTeacher()){
            this.gradedTests.put(gradedTest,score);
            this.solvedTests.remove(gradedTest);
        }
    }

    public void setTest(String testName, long testID,int timeLimit, Subject testSubject ,
                        String m1,String m1A,String m1B,String m1C,String m1D,char m1Correct,
                        String m2,String m2A,String m2B,String m2C,String m2D,char m2Correct,
                        String m3,String m3A,String m3B,String m3C,String m3D,char m3Correct,
                        String m4,String m4A,String m4B,String m4C,String m4D,char m4Correct,
                        String m5,String m5A,String m5B,String m5C,String m5D,char m5Correct,
                        String m6,String m6A,String m6B,String m6C,String m6D,char m6Correct,
                        String m7,String m7A,String m7B,String m7C,String m7D,char m7Correct,
                        String m8,String m8A,String m8B,String m8C,String m8D,char m8Correct,
                        String m9,String m9A,String m9B,String m9C,String m9D,char m9Correct,
                        String m10,String m10A,String m10B,String m10C,String m10D,char m10Correct,
                        String e1) throws InvalidTestException {

        Test newTest = new Test();
        newTest.setTestName(testName);
        newTest.setTestID(testID);
        newTest.setTimeLimit(timeLimit);
        newTest.setTestSubject(testSubject);
        ArrayList<MultipleChoiceQuestion> mcqs = new ArrayList<>();
        mcqs.add(new MultipleChoiceQuestion(m1,m1A,m1B,m1C,m1D,m1Correct));
        mcqs.add(new MultipleChoiceQuestion(m2,m2A,m2B,m2C,m2D,m2Correct));
        mcqs.add(new MultipleChoiceQuestion(m3,m3A,m3B,m3C,m3D,m3Correct));
        mcqs.add(new MultipleChoiceQuestion(m4,m4A,m4B,m4C,m4D,m4Correct));
        mcqs.add(new MultipleChoiceQuestion(m5,m5A,m5B,m5C,m5D,m5Correct));
        mcqs.add(new MultipleChoiceQuestion(m6,m6A,m6B,m6C,m6D,m6Correct));
        mcqs.add(new MultipleChoiceQuestion(m7,m7A,m7B,m7C,m7D,m7Correct));
        mcqs.add(new MultipleChoiceQuestion(m8,m8A,m8B,m8C,m8D,m8Correct));
        mcqs.add(new MultipleChoiceQuestion(m9,m9A,m9B,m9C,m9D,m9Correct));
        mcqs.add(new MultipleChoiceQuestion(m10,m10A,m10B,m10C,m10D,m10Correct));
        newTest.setMcqs(mcqs);
        newTest.setExplainingQuestion(new ExplainingQuestion(e1));
        this.addTest(newTest);

    }

    public void solveTest(Test test, char a1,char a2,char a3,char a4,char a5,char a6, char a7,char a8,char a9,char a10
    , String explainingAnswer) throws InvalidLoggerException, InvalidTestException {
        // A logger will call this method--> this Logger needs to be a Student.
        //in this method we convert mcq answers to MultipleChoiceAnswer objects and store them as an array list. then we
        //add the last of the element of the array list which would be the ExplainingAnswer object the we will create

        if(!this.isStudent())
            throw new InvalidLoggerException("The test needs to be solved by a student");

        ArrayList studentAnswer = new ArrayList();
        ArrayList<MultipleChoiceQuestion> multipleChoiceQuestions = test.getMcqs();
        {
            //mcq 1
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a1);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(0));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 2
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a2);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(1));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 3
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a3);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(2));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 4
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a4);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(3));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 5
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a5);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(4));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 6
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a6);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(5));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 7
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a7);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(6));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 8
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a8);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(7));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 9
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a9);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(8));
            studentAnswer.add(multipleChoiceAnswer);
        }

        {
            //mcq 10
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
            multipleChoiceAnswer.setAns(a10);
            multipleChoiceAnswer.setMcq(multipleChoiceQuestions.get(9));
            studentAnswer.add(multipleChoiceAnswer);
        }

        ExplainingAnswer explainingAnswerObject = new ExplainingAnswer();
        explainingAnswerObject.setQuestion(test.getExplainingQuestion());
        explainingAnswerObject.setAnswer(explainingAnswer);
        studentAnswer.add(explainingAnswerObject);

        this.addStudentAnswers(test,studentAnswer);

        this.addSolvedTest(test);


    }

    public void addStudentAnswers(Test test,ArrayList studentAnswer){
        studentAnswers.put(test,studentAnswer);
    }

    public void gradeTest(Test test,  ArrayList<MultipleChoiceAnswer> mcqAns, double explainingScore)
            throws InvalidTestException {

        double score = 0;
        //the mcqs are graded first here
        for(int i=0; i< test.getMcqs().size();i++){
            if(test.getMcqs().get(i).getCorrectOption() == mcqAns.get(i).getAns()){
                score = score+1;
            }
        }
        //the explaining question's points need to be added here
        score = score + explainingScore;

        // now we can add this test as a graded test
        this.addGradedTests(test,score);

    }


    public void addLogger(String name,long ID,ArrayList<Subject> subjects,
                          boolean isStudent,boolean isTeacher,
                          boolean isExamSetter) throws InvalidLoggerException {

        this.setName(name);
        this.setID(ID);
        this.setSubjects(subjects);
        this.setStudent(isStudent);
        this.setTeacher(isTeacher);
        this.setExamSetter(isExamSetter);

        allLoggers.add(this);

        if(isStudent){//if the logger is a student then we add tests relative to the student's subjects
            //in the tests list.
            for(int i=0; i<allTestsEver.size();i++){

                if(Subject.hasSubject(this,allTestsEver.get(i).getTestSubject())){
                    tests.add(allTestsEver.get(i));
                }

            }
        }

    }

    public void printTheString(String out){
        System.out.println(out);
    }
}
