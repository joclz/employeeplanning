import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeleteEinsatzDialogComponent} from './delete-einsatz-dialog.component';

describe('DeleteEinsatzDialogComponent', () => {
  let component: DeleteEinsatzDialogComponent;
  let fixture: ComponentFixture<DeleteEinsatzDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteEinsatzDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteEinsatzDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //it('should create', () => {
 //   expect(component).toBeTruthy();
 // });
});
