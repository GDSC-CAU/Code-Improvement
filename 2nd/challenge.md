# Challenge for 2nd
## Implementation
To do
- [ ] Implement a class "Fan"

class Fan implements EngineControl
```java
interface EngineControl {
    void on() throws PowerExecption;
    void off();
    void powerUp() throws EngineError;
    void powerDown() throws EngineError;
}
```

You can implement anything else you want if you think that is required.  
Also, you can modify the given interface if you think that is better.