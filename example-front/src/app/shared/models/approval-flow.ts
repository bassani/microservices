export interface IApprovalFlow {
  inputStyle?: string;
  dark?: boolean;
  theme?: string;
  ripple?: boolean;
}

export class ApprovalFlow implements IApprovalFlow {
  inputStyle?: string;
  dark?: boolean;
  theme?: string;
  ripple?: boolean;

  constructor(data: IApprovalFlow) {
    this.inputStyle = (data && data.inputStyle) || '';
    this.dark = (data && data.dark) || false;
    this.theme = (data && data.theme) || '';
    this.ripple = (data && data.ripple) || false;
  }
}