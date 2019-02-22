import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/routing/route_paths.dart';
import 'package:zoran.io/services/resource_service.dart';

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
  ResourceResponse model;

  final Router _router;
  final NewResourceService _newResourceService;
  ResourceMinifiedComponent(this._router, this._newResourceService);

  void add(ResourceResponse templateUri) {
    if(!_newResourceService.request.templatesUsed.contains(templateUri.id)) {
      _newResourceService.request.templatesUsed.add(templateUri.id);
    }
  }

  bool isInEdit() {
    return _newResourceService.request != null;
  }

  bool isAdded() {
    return isInEdit() &&
        _newResourceService.request.templatesUsed.contains(model.id);
  }

  void navigate(String uri) async {
    var url = RoutePaths.resource.toUrl(parameters: {uriParam: '$uri'});
    _router.navigate(url);
  }
}