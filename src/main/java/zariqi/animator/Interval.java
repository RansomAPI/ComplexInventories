package zariqi.animator;

/**
 * Interval
 */
public class Interval
{
    private long interval;
    private long last;

    public Interval(long interval)
    {
        this.interval = interval;
        this.last = System.currentTimeMillis();
    }

    public boolean elapsed()
    {
        //Check if the elapsed time equals the interval, if true: that means that the event get's executed!
        if (System.currentTimeMillis() - last > interval)
        {
            last = System.currentTimeMillis();
            return true;
        }

        return false;
    }

    public long getInterval()
    {
        return interval;
    }
}
