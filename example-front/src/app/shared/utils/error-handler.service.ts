import { MessageService } from "primeng/api";

export class ErrorHandler {
    static _message = new MessageService();
    constructor(){}
    
    static handleHttpError(summary: string, httpError: any) {
        this._message.add({summary: summary, detail: httpError.error.message || 'Tente novamente mais tarde', severity: 'error'})
    }

    static handleUnexpectedError( error: any) {
        this._message.add({summary: 'Ops! algo não esta certo', detail: error?.error?.message || 'Tente novamente mais tarde', severity: 'error'})
    }
}