import { AbstractControl } from '@angular/forms';

export class RDValidators {

    /**
   * Validate that at least one of the informed controls have a truthy value
   * @param formGroup fromgroup that contains the controls
   * @param labels list of controls names
   * @Optional @param message message to be shown in case of error
   */
  static atLeastOneTruthy(formGroup: AbstractControl, labels: string[], message: string) {
    const DEFAULT_MESSAGE = `Complete todos os campos requeridos`;
    let controls = labels.map(label => formGroup.get(label)?.value)
    .filter(control => Array.isArray(control) ? control.length > 0 : !!control)
    let error = null;
    if(controls.length == 0) error = {atLeastFields: {value: message || DEFAULT_MESSAGE}}
    return error
  }
}