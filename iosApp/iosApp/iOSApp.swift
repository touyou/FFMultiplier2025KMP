import SwiftUI
import FirebaseCore
import Shared

@main
struct iOSApp: App {
  @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
  
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
  
  init() {
    SharedKt.doInitIosKoin(
      onKoinStart: {
        Shared_iosKt.createSwiftLibDependencyModule(
          factory: SwiftLibDependencyFactory.shared
        )
      }
    )
  }
}

class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
    FirebaseApp.configure()
    return true
  }
}
