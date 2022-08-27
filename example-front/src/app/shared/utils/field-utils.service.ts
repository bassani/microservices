import { AbstractControl, FormControl } from "@angular/forms";

export class FieldUtilsService {
    

    isRequired(control: FormControl) {
        if(!control || !control.validator) return false;
        return control.validator('' as unknown as AbstractControl)?.required;
      }
}