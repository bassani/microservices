export interface IUserAuthRequest {
  email: string;
  password: string;
}

export interface IUser {
  registrationNumber?: number;
  keycloakUserId: string;
  firstName: string;
  lastName: string;
  areaCode?: number;
  email: string;
  phoneNumber: string;
  accessProfile?: number;
  activationStatus: boolean;
  vacationReturnDate: string;
  fullName: string;

}

export class User {
  email: string;
  loggedIn: boolean;
  token: string | null;
  name: string;
  id: number;
  constructor(user: User) {
    this.email = user.email;
    this.loggedIn = user.loggedIn;
    this.name = user.name;
    this.token = user.token;
    this.id = user.id || 0;
    if (user.token) {
      localStorage.setItem('token', user.token);
    } else {
      localStorage.removeItem('token');
    }
  }
}
