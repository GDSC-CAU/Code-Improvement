public class PowerException : System.Exception { }
public class EngineException : System.Exception { }

public interface IEngineControl
{
    /// <exception cref="PowerException"></exception>
    void On();

    void Off();

    /// <exception cref="EngineException"></exception>
    void PowerUp();

    /// <exception cref="EngineException"></exception>
    void PowerDown();
}

private enum FanStatus { On, Off }
private enum FanPower { High, Medium, Low }
public class Fan : IEngineControl 
{
    private FanStatus status = FanStatus.Off;
    private FanPower power = FanPower.Low;
    public void On()
    {
        status = FanStatus.On;
        if (status is not FanStatus.On)
            throw new PowerException();
    }

    public void Off() => status = FanStatus.Off;
    

    public void PowerUp()
    {
        power = power switch {
            FanPower.Low => FanPower.Medium,
            _ => FanPower.High
        };
        // 오류나면
        // throw new EngineException
    }

    public void PowerDown()
    {
        power = power switch {
            FanPower.High => FanPower.Medium,
            _ => FanPower.Low
        };
        // 오류나면
        // throw new EngineException
    }
}