import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MitarbeiterStatus} from "../../models/mitarbeiter/mitarbeiter-status.enum";
import {TableMitarbeiterComponent} from "./table-mitarbeiter.component";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

@Component({
  selector: 'app-search-mitarbeiter',
  templateUrl: './search-mitarbeiter.component.html',
  styleUrls: ['./search-mitarbeiter.component.css']
})
export class SearchMitarbeiterComponent implements OnInit {

  filteredLastEndDateMitarbeiter: Observable<MitarbeiterDTO[]>;
  lastEndDate = new FormControl({value: '', disabled: true});
  lastEndDateMitarbeiter = new FormControl('', [Validators.required]);
  lastEndDateFormGroup: FormGroup;

  filteredChanceMitarbeiter: Observable<MitarbeiterDTO[]>;
  chance = new FormControl({value: '', disabled: true});
  chanceMitarbeiter = new FormControl('', [Validators.required]);
  chanceFormGroup: FormGroup;

  mitarbeiterEinsatz = new FormControl({value: '', disabled: true});
  mitarbeiterEinsatzFormGroup: FormGroup;

  subunternehmerEinsatz = new FormControl({value: '', disabled: true});
  subunternehmerEinsatzFormGroup: FormGroup;

  deckungsbeitrag = new FormControl({value: '', disabled: true});
  deckungsbeitragFormGroup: FormGroup;

  isMitarbeiterBank = false;
  mitarbeiterBankFormGroup: FormGroup;

  isMitarbeiterInternBank = false;
  mitarbeiterInternBankFormGroup: FormGroup;

  mitarbeiterList: Array<MitarbeiterDTO>;

  labelMitarbeiterEinsatz: string;
  labelChanceMitarbeiter: string;

  @ViewChild('tableMitarbeiterBank') tableMitarbeiterBank: TableMitarbeiterComponent;
  @ViewChild('tableMitarbeiterInternBank') tableMitarbeiterInternBank: TableMitarbeiterComponent;

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
  }

  ngOnInit(): void {
    this.mitarbeiterService.getMitarbeiterListOrderByName().subscribe(result => {
      this.mitarbeiterList = result;

      this.filteredLastEndDateMitarbeiter = this.getFilteredOptions(this.lastEndDateMitarbeiter);
      this.filteredChanceMitarbeiter = this.getFilteredOptions(this.chanceMitarbeiter);
    });

    this.lastEndDateFormGroup = new FormGroup({
      lastEndDate: this.lastEndDate,
      lastEndDateMitarbeiter: this.lastEndDateMitarbeiter
    });
    this.chanceFormGroup = new FormGroup({
      chanceMitarbeiter: this.chanceMitarbeiter,
      chance: this.chance
    });
    this.mitarbeiterEinsatzFormGroup = new FormGroup({
      mitarbeiterEinsatz: this.mitarbeiterEinsatz
    });
    this.subunternehmerEinsatzFormGroup = new FormGroup({
      subunternehmerEinsatz: this.subunternehmerEinsatz
    });
    this.deckungsbeitragFormGroup = new FormGroup({
      deckungsbeitrag: this.deckungsbeitrag
    });
    this.mitarbeiterBankFormGroup = new FormGroup({});
    this.mitarbeiterInternBankFormGroup = new FormGroup({});
  }

  private getFilteredOptions(formControl: FormControl): Observable<MitarbeiterDTO[]> {
    return formControl.valueChanges.pipe(
      startWith(''),
      map(value => typeof value === "string" ? value : value ? value.name : ''),
      map(name => name ? this._filter(name) : this.mitarbeiterList.slice())
    );
  }

  private _filter(value: string): MitarbeiterDTO[] {
    const filterValue = value.toLowerCase();
    return this.mitarbeiterList.filter(mitarbeiter => mitarbeiter.name.toLowerCase().indexOf(filterValue) === 0);
  }

  displayFn(mitarbeiter: MitarbeiterDTO): string {
    return mitarbeiter && mitarbeiter.name ? mitarbeiter.name : '';
  }

  getLastEndDateOnSubmit() {
    this.mitarbeiterService.getLastEndDateForMitarbeiter(this.lastEndDateMitarbeiter.value.id).subscribe(result => {
      if (result) {
        this.labelMitarbeiterEinsatz = this.lastEndDateMitarbeiter.value.name + " ist im Einsatz bis"
        this.lastEndDate.setValue(new Date(result).toLocaleDateString());
      } else {
        this.labelMitarbeiterEinsatz = "";
        this.lastEndDate.setValue(this.lastEndDateMitarbeiter.value.name + " ist nicht im Einsatz");
      }
      this.lastEndDateMitarbeiter.reset();
    });
  }

  getChanceOnSubmit() {
    this.mitarbeiterService.getChanceForMitarbeiter(this.chanceMitarbeiter.value.id)
      .subscribe(result => {
        this.chance.setValue(result.toString());
        this.labelChanceMitarbeiter = "Chance auf Verlängerung für " + this.chanceMitarbeiter.value.name;
        this.chanceMitarbeiter.reset();
      });
  }

  getMitarbeiterEinsatzOnSubmit() {
    this.mitarbeiterService.getMitarbeiterImEinsatz(MitarbeiterStatus.ANGESTELLT)
      .subscribe(result => this.mitarbeiterEinsatz.setValue(result.toString()));
  }

  getSubunternehmerEinsatzOnSubmit() {
    this.mitarbeiterService.getMitarbeiterImEinsatz(MitarbeiterStatus.SUBUNTERNEHMER)
      .subscribe(result => this.subunternehmerEinsatz.setValue(result.toString()));
  }

  getDeckungsbeitragOnSubmit() {
    this.mitarbeiterService.getDeckungsbeitrag().subscribe(result => this.deckungsbeitrag.setValue(result.toString()));
  }

  getMitarbeiterBankOnSubmit() {
    this.initMitarbeiterBank();
    this.isMitarbeiterBank = true;
  }

  initMitarbeiterBank() {
    if (this.tableMitarbeiterBank) {
      this.tableMitarbeiterBank.ngOnInit();
    }
  }

  getMitarbeiterInternBankOnSubmit() {
    this.initMitarbeiterInternBank();
    this.isMitarbeiterInternBank = true;
  }

  initMitarbeiterInternBank() {
    if (this.tableMitarbeiterInternBank) {
      this.tableMitarbeiterInternBank.ngOnInit();
    }
  }
}
