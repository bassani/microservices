import { environment } from 'src/environments/environment';

export class Path {
  static HTTP_MENU_PATH = environment.BASE_URL + '/menus';
  static HTTP_AUTH_PATH = environment.BASE_URL + '/auth';
  static HTTP_API_BASE = environment.BASE_URL;
}
