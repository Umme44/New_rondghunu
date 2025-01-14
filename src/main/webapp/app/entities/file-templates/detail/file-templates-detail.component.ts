import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFileTemplates } from '../file-templates.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-file-templates-detail',
  templateUrl: './file-templates-detail.component.html',
})
export class FileTemplatesDetailComponent implements OnInit {
  fileTemplates: IFileTemplates | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileTemplates }) => {
      this.fileTemplates = fileTemplates;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
