import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmployeeStaticFile } from '../employee-static-file.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../employee-static-file.test-samples';

import { EmployeeStaticFileService } from './employee-static-file.service';

const requireRestSample: IEmployeeStaticFile = {
  ...sampleWithRequiredData,
};

describe('EmployeeStaticFile Service', () => {
  let service: EmployeeStaticFileService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmployeeStaticFile | IEmployeeStaticFile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmployeeStaticFileService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a EmployeeStaticFile', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const employeeStaticFile = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(employeeStaticFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmployeeStaticFile', () => {
      const employeeStaticFile = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(employeeStaticFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmployeeStaticFile', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmployeeStaticFile', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmployeeStaticFile', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmployeeStaticFileToCollectionIfMissing', () => {
      it('should add a EmployeeStaticFile to an empty array', () => {
        const employeeStaticFile: IEmployeeStaticFile = sampleWithRequiredData;
        expectedResult = service.addEmployeeStaticFileToCollectionIfMissing([], employeeStaticFile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employeeStaticFile);
      });

      it('should not add a EmployeeStaticFile to an array that contains it', () => {
        const employeeStaticFile: IEmployeeStaticFile = sampleWithRequiredData;
        const employeeStaticFileCollection: IEmployeeStaticFile[] = [
          {
            ...employeeStaticFile,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmployeeStaticFileToCollectionIfMissing(employeeStaticFileCollection, employeeStaticFile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmployeeStaticFile to an array that doesn't contain it", () => {
        const employeeStaticFile: IEmployeeStaticFile = sampleWithRequiredData;
        const employeeStaticFileCollection: IEmployeeStaticFile[] = [sampleWithPartialData];
        expectedResult = service.addEmployeeStaticFileToCollectionIfMissing(employeeStaticFileCollection, employeeStaticFile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employeeStaticFile);
      });

      it('should add only unique EmployeeStaticFile to an array', () => {
        const employeeStaticFileArray: IEmployeeStaticFile[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const employeeStaticFileCollection: IEmployeeStaticFile[] = [sampleWithRequiredData];
        expectedResult = service.addEmployeeStaticFileToCollectionIfMissing(employeeStaticFileCollection, ...employeeStaticFileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const employeeStaticFile: IEmployeeStaticFile = sampleWithRequiredData;
        const employeeStaticFile2: IEmployeeStaticFile = sampleWithPartialData;
        expectedResult = service.addEmployeeStaticFileToCollectionIfMissing([], employeeStaticFile, employeeStaticFile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employeeStaticFile);
        expect(expectedResult).toContain(employeeStaticFile2);
      });

      it('should accept null and undefined values', () => {
        const employeeStaticFile: IEmployeeStaticFile = sampleWithRequiredData;
        expectedResult = service.addEmployeeStaticFileToCollectionIfMissing([], null, employeeStaticFile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employeeStaticFile);
      });

      it('should return initial array if no EmployeeStaticFile is added', () => {
        const employeeStaticFileCollection: IEmployeeStaticFile[] = [sampleWithRequiredData];
        expectedResult = service.addEmployeeStaticFileToCollectionIfMissing(employeeStaticFileCollection, undefined, null);
        expect(expectedResult).toEqual(employeeStaticFileCollection);
      });
    });

    describe('compareEmployeeStaticFile', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmployeeStaticFile(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmployeeStaticFile(entity1, entity2);
        const compareResult2 = service.compareEmployeeStaticFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmployeeStaticFile(entity1, entity2);
        const compareResult2 = service.compareEmployeeStaticFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmployeeStaticFile(entity1, entity2);
        const compareResult2 = service.compareEmployeeStaticFile(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
