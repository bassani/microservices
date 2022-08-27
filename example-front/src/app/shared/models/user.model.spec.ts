import { User } from "./user.model";

describe('UserModel', () => {
  it('should create an instance', () => {
    expect(new User({
        email: '',
        token: '123',
        id: 1,
        loggedIn: true,
        name: 'teste'
    })).toBeTruthy();
  });

  it('should create an instance without token', () => {
    expect(new User({
        email: '',
        token: null,
        id: 0,
        loggedIn: true,
        name: 'teste'
    })).toBeTruthy();
  });
});
