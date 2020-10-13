import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {EinsatzDialogData} from "../../models/einsatz/einsatz-dialog-data";

@Component({
  selector: 'app-delete-einsatz-dialog',
  templateUrl: './delete-einsatz-dialog.component.html',
  styleUrls: ['./delete-einsatz-dialog.component.css']
})
export class DeleteEinsatzDialogComponent {

  constructor(public dialogRef: MatDialogRef<DeleteEinsatzDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: EinsatzDialogData) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onYesClick() {
    this.dialogRef.close(this.data.id);
  }
}
