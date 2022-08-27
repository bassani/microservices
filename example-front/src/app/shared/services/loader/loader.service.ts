import { Injectable } from '@angular/core';

/**
 * Loader service in change of managing the state of loading in the application
 */
@Injectable({
  providedIn: 'root'
})
export class LoaderService {

  private _loaders = 0;

  /**
   * Returns if there is any loader active
   * use this to know if something is loading in the page
   * ```typescript
   * loadersService.loading ? 'is-loading' : 'not-loading'
   * ```
   */
  get loading() {
    return this._loaders > 0;
  }

  /**
   * Starts a new loader
   * ```typescript
   * loadersService.loading // this should be false;
   * loaderService.start();
   * loadersService.loading // this should be true now;
   * ```
   */
  start() {
    this._loaders += 1;
  }

   /**
   * Stops a loader
   * ```typescript
   * loaderService.start();
   * loadersService.loading // this should be trye now;
   * loaderService.stop();
   * loadersService.loading // this should be false now;
   * ```
   */
  stop() {
    if(this._loaders == 0) return
    this._loaders -= 1;
  }
  

}
