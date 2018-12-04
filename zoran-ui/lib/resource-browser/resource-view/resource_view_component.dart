import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';

@Component(
  selector: 'resource-view',
  styleUrls: const ['resource_view_component.scss.css'],
  templateUrl: 'resource_view_component.html',
  directives: const [
    coreDirectives
  ],
  providers: const
  [
    materialProviders
  ],
)
class ResourceViewComponent {
}