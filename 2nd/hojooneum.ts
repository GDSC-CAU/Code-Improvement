// interface EngineControl {
//     void on() throws PowerExecption;
//     void off();
//     void powerUp() throws EngineError;
//     void powerDown() throws EngineError;
// }

interface EngineControl {
    on: () => void;
    off: () => void;
    powerUp: () => void;
    powerDown: () => void;
}
class PowerExecption extends Error {
    constructor(message: string) {
        super(message);
    }
}
class EngineError extends Error {
    constructor(message: string) {
        super(message);
    }
}
class Fan implements EngineControl {
    private fanSpeed: number = 0;
    private engine: Engine;
    constructor(engine: Engine) {
        this.engine = engine;
    }
    public async on(): Promise<any> {
        await this.engine.on();
        this.fanSpeed = this.engine.getPower() * 10;
    }
    public off(): void {
        this.engine.off();
    }
    public async powerUp(): Promise<any> {
        await this.engine.powerUp();
        this.fanSpeed = this.engine.getPower() * 10;
    }
    public async powerDown(): Promise<any> {
        await this.engine.powerDown();
        this.fanSpeed = this.engine.getPower() * 10;
    }
}
