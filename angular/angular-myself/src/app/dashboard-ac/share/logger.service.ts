import { Injectable } from '@angular/core';
import { logSwitch } from './config-dev'

@Injectable()
export class LoggerService {

  constructor() { }
  logs: string[] = [];
  prevMsg = '';
  prevMsgCount = 1;


  log(msg: string) {
    if (msg === this.prevMsg) {
      // Repeat message; update last log entry with count.
      this.logs[this.logs.length - 1] = msg + ` (${this.prevMsgCount += 1}x)`;
    } else {
      // New message; log it.
      this.prevMsg = msg;
      this.prevMsgCount = 1;
      this.logs.push(msg);
    }
    //console.log(">developer:");
    //console.log(msg);
  }
  consoleLog(l: any) {
    if (logSwitch) {
      console.log(l);
    }
  }
  consoleError(e: any) {
    if (logSwitch) {
      console.error(e);
      // console.log(e);
    }
  }
  clear() { this.logs.length = 0; }

  // schedules a view refresh to ensure display catches up
  tick() { this.tick_then(() => { }); }
  tick_then(fn: () => any) { setTimeout(fn, 0); }
}
