# Java 2D Game Engine
2D Game engine running in Java. 

## Features
#### Delta Timing
Ensures consistant updating regardless of system performance. Keeps game at target frame rate.
#### Display Manager
- Dynamically scales game to fit any window size.
- Maintains aspect ratio of base resolution.
- Full screen support.
- Corrects mouse position.
#### Simple programing
- Program graphics relative to base resolution. The display manager will scale and adjust accordingly.
- Program updates using the given delta value. Use this value to scale all time based actions (such as speed, pixels/second).
- Input Manager handles checking if keys or mouse buttons are pressed, released, or held.

## TODO
- [ ] Find good name for engine.
- [ ] Ensure proper frame timing.
- [ ] Re-work room and entity system (and maybe sprite system).
- [ ] Implement buffer strategy.
- [ ] Implement gamepad support.
- [ ] Implement resolution scaling system.
- [ ] Implement anti-aliasing solution.
- [ ] Make game using engine to demonstrate.

## How to use
In its current state I do not reccomend that you use this engine for your game as there are still some kinks to be worked out. The level and object systems are planned to be re-worked and the engine serves more of a tech demo purpose at the moment.
