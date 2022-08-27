import { ApprovalFlow } from './approval-flow';

describe('ApprovalFlowModel', () => {
  it('should create an instance', () => {
    expect(new ApprovalFlow({
      inputStyle: '',
      dark: true,
      theme: '',
      ripple: true
    })).toBeTruthy();
  });
});
