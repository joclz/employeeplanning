import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MitarbeiterDialogData} from "../../models/mitarbeiter/mitarbeiter-dialog-data";

@Component({
  selector: 'app-delete-mitarbeiter-dialog',
  templateUrl: './delete-mitarbeiter-dialog.component.html',
  styleUrls: ['./delete-mitarbeiter-dialog.component.css']
})
export class DeleteMitarbeiterDialogComponent {

  constructor(public dialogRef: MatDialogRef<DeleteMitarbeiterDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: MitarbeiterDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onYesClick() {
    this.dialogRef.close(this.data.id);
  }

}
