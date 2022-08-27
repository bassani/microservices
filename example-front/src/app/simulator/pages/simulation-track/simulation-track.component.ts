import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { SimulateService } from '../../services/simulate/simulate.service';
import { MessageService } from 'primeng/api';
import { BehaviorSubject } from 'rxjs';

/**
 * Componente encarregado de listar as simulações e seus status
 */
@Component({
  selector: 'app-simulation-track',
  templateUrl: './simulation-track.component.html',
  styleUrls: ['./simulation-track.component.scss']
})
export class SimulationTrackComponent implements OnInit {
  form: FormGroup;
  loading: boolean = false;
  simulation$ = new BehaviorSubject<any>({
    page: 0,
    size: 10,
    numberOfElements: 0,
    content: [],
    number: 0,
    totalElements: 0,
  });
  constructor(
    private _fb: FormBuilder,
    private _message: MessageService,
    private _simulationService: SimulateService,
  ) { }

  ngOnInit(): void {
    this.createForm();
  }


  /**
   * Cria o formulario com os filtros da tela
   */
  createForm() {
    this.form = this._fb.group(
      {
        registerDate: [null, Validators.compose([Validators.required])],
        status: [null],
        simulationType: [null],
        classification: [null],
        user: [null],
        supplier: [null],
        manufacturer: [null]
      },
    );
  }

  /**
   * retorna um form control de um group, sugar sintax de acessar o form control
   * @param lb nome do form control
   */
  getControl(lb: string): FormControl {
    return this.form.get(lb) as FormControl;
  }

  /** Efetua a paginação baseado no evento de page atual */
  paginate($event: any) {
    const { page, rows } = $event;
    this.changePage(page, rows);
  }

  /** Retorna valores de uma lista separada por virgula ou 3+ fornecedores pai */
  asList(arr: {parentSupplierName: string}[] = []) {
    return arr.length > 3 ? '3+ fornecedores pai' : arr.map((e: {parentSupplierName: string}) => e.parentSupplierName).join(', ')
  }

  /**
   * Recebe uma pagina, tamanho e filtros e chama o serviço
   * para listar as simulações baseado neles
   */
  changePage(page: number, size: number): void {
    let values = this.form.getRawValue();
    const filters = {
      ...values,
    }
    this.loading = true;
    this._simulationService.findSimulations(filters, page, size).subscribe((data: any) => {
    this.simulation$.next(data);
    this.loading = false;
    }, err => {
      this.loading = false;
      this.simulation$.next({...this.simulation$.value, content: []})
    })
  }

  /**
   * Verifica o status do form e chama o changePage para pesquisar
   */
  search(page: 0, size: 10) {
    this.form.markAllAsTouched();
    if (this.form.invalid){
      return this._message.add({
        severity: 'error',
        summary: 'Verifique os campos requeridos',
        detail: 'Formulário inválido',
        key: 'main'
      });
    }

    let values = this.form.getRawValue();
    const filters = {
      ...values,
    }
    this.loading = true;
    this._simulationService.findSimulations(filters, page, size).subscribe((data: any) => {
      if(data.content.length > 0 ){
        this.simulation$.next(data);
        this.loading = false;
      }else{
          this._message.add({
          severity: 'warn',
          summary: 'Nenhum resultado foi encontrado',
          detail: 'Não foi possível encontrar nenhum resultado para o filtro aplicado',
          key: 'main'
        });
        this.loading = false;
      }
    }, err => {
      this.loading = false;
      this.simulation$.next({...this.simulation$.value, content: []})
    })

  }

}
