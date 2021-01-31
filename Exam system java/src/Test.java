import java.util.ArrayList;

public class Test {
    private String testName;
    private long testID;
    private int timeLimit;
    private Subject testSubject;
    private ArrayList<MultipleChoiceQuestion> mcqs = new ArrayList<>();
    private ExplainingQuestion explainingQuestion;

    public ExplainingQuestion getExplainingQuestion() {
        return explainingQuestion;
    }

    public void setExplainingQuestion(ExplainingQuestion explainingQuestion) {
        this.explainingQuestion = explainingQuestion;
    }

    public ArrayList<MultipleChoiceQuestion> getMcqs() {
        return mcqs;
    }

    public void setMcqs(ArrayList<MultipleChoiceQuestion> mcqs) {
        this.mcqs = mcqs;
    }

    public Test(){
        testID = 0;
        testName = "";
        testSubject = new Subject();
    }

    public Subject getTestSubject() {
        return testSubject;
    }

    public void setTestSubject(Subject testSubject) {
        this.testSubject = testSubject;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public long getTestID() {
        return testID;
    }

    public void setTestID(long testID) {
        this.testID = testID;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public static boolean testIDComparer(Test t1, Test t2)throws InvalidTestException{
        if(t1.getTestID() == 0 || t1.getTestName().equals("") || t2.getTestName().equals("") || t2.getTestID() == 0 )
            throw new InvalidTestException("The test you are looking for doesn't exist");
        if(t1.getTestID() == t2.getTestID())
            return true;
        return false;
    }
}
