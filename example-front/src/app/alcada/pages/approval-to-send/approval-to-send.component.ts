import { SimulateFollowUpService } from './../../../shared/services/simulate-follow-up/simulate-follow-up.service';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';


@Component({
	selector: 'app-approval-to-send',
	templateUrl: './approval-to-send.component.html',
	styleUrls: ['./approval-to-send.component.scss']
})
export class ApprovalToSendComponent implements OnInit {

	form: FormGroup;
	simulationsList: any = [];

	constructor(
		private _fb: FormBuilder,
		private _simulateFollowUpService: SimulateFollowUpService
	) {

	}

	ngOnInit() {
		this.initForm();
		this.getApprovalSimulation();
	}

	initForm() {
		this.form = this._fb.group({
			registerDate: [null],
			user: [null],
			supplier: [null],
			manufacturer: [null],
			statusId: 12
		});
	}

	clearForm() {
		this.form.reset();
	}

	getApprovalSimulation() {
		const statusId = {
			statusId: 12
		};
		this._simulateFollowUpService.getApprovalSimulate(statusId).subscribe(value => {
			this.simulationsList = value?.content;
		});
	}

	getControl(lb: string): FormControl {
		return this.form.get(lb) as FormControl;
	}

	submit() {
		let supplierIdList: any = [];
		let parentSupplierIdList: any = [];
		let keycloakUserIdList: any = [];

		const form = this.form.getRawValue();

		form?.manufacturer?.map((item: any) => supplierIdList.push(item.id));
		form?.supplier?.map((item: any) => parentSupplierIdList.push(item.id));
		form?.user?.map((item: any) => keycloakUserIdList.push(item.keyCloakUserId));
		
		const data: any = {
			statusId: 12,
			keycloakUserId: keycloakUserIdList,
			suppliersId: supplierIdList,
			parentSupplierId: parentSupplierIdList,
			registerDate: form?.registerDate
		}
		this._simulateFollowUpService.getApprovalSimulate(data).subscribe(value => {
			this.simulationsList = value?.content;
		});
	}
}