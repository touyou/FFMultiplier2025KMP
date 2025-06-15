import Shared

extension Score: @retroactive Identifiable {
  public var id: String {
    "\(self.userRef)-\(self.updatedAt)"
  }
}
