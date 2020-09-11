import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MitarbeiterVertrieb} from "../../models/vertrieb/mitarbeiter-vertrieb";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterVertriebService} from "../../services/vertrieb/mitarbeiter-vertrieb.service";

const patternNames = Validators.pattern('[a-zA-Z_äÄöÖüÜß\-]*');

@Component({
  selector: 'app-update-vertrieb',
  templateUrl: './update-vertrieb.component.html',
  styleUrls: ['./update-vertrieb.component.css']
})
export class UpdateVertriebComponent implements OnInit {

  @Input() mitarbeiterVertriebInput: MitarbeiterVertrieb;

  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  formGroup: FormGroup;

  id = new FormControl({value: '', disabled: true});
  name = new FormControl('', [Validators.required, patternNames]);
  vorname = new FormControl('', [Validators.required, patternNames]);

  showSuccessMsg: boolean = false;
  showErrorMsg: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterVertriebService: MitarbeiterVertriebService) {
  }

  onSubmit() {
    //TODO Weiterleitung nach Update? Oder nur Meldung ausgeben?

    let mitarbeiterVertrieb = new MitarbeiterVertrieb;
    mitarbeiterVertrieb.id = this.id.value;
    mitarbeiterVertrieb.name = this.name.value;
    mitarbeiterVertrieb.vorname = this.vorname.value;

    this.mitarbeiterVertriebService.update(mitarbeiterVertrieb).subscribe(data => { 
       this.showSuccessMsg = true;
       this.showErrorMsg = false; 
    }, (err) => {
       this.showSuccessMsg = false;
       this.showErrorMsg = true; 
    });
  }
  
  ngOnInit(): void {
    this.id.setValue(this.mitarbeiterVertriebInput.id);
    this.name.setValue(this.mitarbeiterVertriebInput.name);
    this.vorname.setValue(this.mitarbeiterVertriebInput.vorname);

    this.formGroup = new FormGroup({
      id: this.id,
      name: this.name,
      vorname: this.vorname
    });
  }

  ngOnDestroy(): void {
    this.formRef.resetForm();
  }
}
