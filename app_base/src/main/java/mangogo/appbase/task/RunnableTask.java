package mangogo.appbase.task;


public class RunnableTask extends SafeTask<Runnable, Void> {
    @Override
    protected Void doInBackground(Runnable... params) {
        params[0].run();
        return null;
    }
}
