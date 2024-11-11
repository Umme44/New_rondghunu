import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IFlexScheduleApplication } from '../../../shared/legacy/legacy-model/flex-schedule-application.model';

@Component({
  selector: 'jhi-flex-schedule-application-detail-approval-modal',
  templateUrl: './flex-schedule-application-detail-approval-modal.component.html',
})
export class FlexScheduleApplicationDetailApprovalModalComponent implements OnInit {
  flexScheduleApplication: IFlexScheduleApplication | null = null;
  modalType!: string;

  constructor(protected activatedRoute: ActivatedRoute, public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    //this.activatedRoute.data.subscribe(({ flexScheduleApplication }) => (this.flexScheduleApplication = flexScheduleApplication));
  }

  // byteSize(base64String: string): string {
  //   return this.dataUtils.byteSize(base64String);
  // }

  // openFile(contentType = '', base64String: string): void {
  //   this.dataUtils.openFile(contentType, base64String);
  // }

  previousState(): void {
    window.history.back();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}