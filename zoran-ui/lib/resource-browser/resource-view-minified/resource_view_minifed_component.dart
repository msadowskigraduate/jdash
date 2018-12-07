import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:sheets_dashboard/routing/route_paths.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'resource_view_minifed_component',
  styleUrls: const ['resource_view_minifed_component.scss.css',
  'package:angular_components/css/mdc_web/card/mdc-card.scss.css'
  ],
  templateUrl: 'resource_view_minifed_component.html',
  directives: const [
    coreDirectives,
    MaterialIconComponent,
    MaterialButtonComponent
  ],
  providers: const
  [
    materialProviders
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
)
class ResourceMinifiedComponent {
  @Input()
  ProjectDetails model;

  @Input()
  ProjectDetails details;

  final Router _router;
  ResourceMinifiedComponent(this._router);

  void add(ProjectDetails templateUri) {
    if(!details.templates.contains(templateUri)) {
      details.templates.add(templateUri.uri);
      details.details.add(templateUri);
    }
  }

  bool isInEdit() {
    return details != null;
  }

  bool isAdded() {
    return isInEdit() && details.details.contains(model);
  }

  void navigate(String uri) async {
    var url = RoutePaths.resource.toUrl(parameters: {uriParam: '$uri'});
    _router.navigate(url);
  }
}