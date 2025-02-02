import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { swalClose, swalOnLoading, swalOnSavedSuccess } from '../../shared/swal-common/swal-common';
import { IEmployeeStaticFile } from '../../shared/model/employee-static-file.model';
import { ImportEmployeeImageService } from 'app/admin/import-data/import-employee-image.service';

@Component({
  selector: 'jhi-employee-image-upload',
  templateUrl: './import-employee-image-upload.component.html',
})
export class ImportEmployeeImageUploadComponent implements OnInit, OnDestroy {
  selectedFiles: File[] = [];
  isSaving!: boolean;
  id!: number;
  employeeStaticFile!: IEmployeeStaticFile;
  fileValidationMessage = '';

  size: any;
  width!: number;
  height!: number;

  createFrom = new FormGroup({
    photo: new FormControl(),
    fullName: new FormControl(),
  });

  constructor(protected employeeStaticFileService: ImportEmployeeImageService, protected router: Router, protected route: ActivatedRoute) {}

  ngOnInit(): void {}

  ngOnDestroy(): void {}

  // updateForm(employeeStaticFile: IEmployeeStaticFile): void {
  //   this.createFrom.patchValue({
  //     id: employeeStaticFile.id,
  //     filePath: employeeStaticFile.filePath,
  //     employeeId: employeeStaticFile.employeeId,
  //   });
  // }

  onChange(event: any): void {
    this.selectedFiles = event.target.files;

    const image: any = event.target.files[0];
    this.size = image.size;
    const fr = new FileReader();
    fr.onload = () => {
      const img = new Image();

      img.onload = () => {
        this.width = img.width;
        this.height = img.height;
      };

      if (typeof fr.result === 'string') {
        img.src = fr.result;
      } // This is the data URL
    };
  }

  onSubmit(): void {
    this.fileValidationMessage = '';
    if (this.selectedFiles.length > 0) {
      swalOnLoading('Uploading Id Card');
      this.employeeStaticFileService.uploadImageFiles(this.selectedFiles).subscribe(() => {
        swalClose();
        swalOnSavedSuccess();
        setTimeout(() => {
          this.router.navigate(['/import-data/import-employee-image']);
        }, 1000);
      });
    } else {
      this.fileValidationMessage = 'No File Selected';
    }
  }

  back(): void {
    window.history.back();
  }
}
