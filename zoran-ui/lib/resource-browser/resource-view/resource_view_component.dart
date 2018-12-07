import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_router/angular_router.dart';
import 'package:sheets_dashboard/routing/route_paths.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'resource-view',
  styleUrls: const ['resource_view_component.scss.css'],
  templateUrl: 'resource_view_component.html',
  directives: const [
    coreDirectives,
    formDirectives,
    MaterialAutoSuggestInputComponent,
    MaterialButtonComponent,
    MaterialCheckboxComponent,
    MaterialDropdownSelectComponent,
    materialInputDirectives,
  ],
  providers: const
  [
    materialProviders
  ],
)
class ResourceViewComponent implements OnActivate {

  @Input()
  ProjectDetails details;
  bool disabled = false;

  final Router router;
  final ZoranService service;

  ResourceViewComponent(this.router, this.service);

  @override
  void onActivate(RouterState previous, RouterState current) async {
    if(details == null) {
      String uri = getUrl(current.parameters);
      details = await service.getResourceByItsId(uri);
      disabled = false;
    }
    else {
      disabled = true;
    }
  }
}