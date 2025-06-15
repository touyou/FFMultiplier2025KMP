import Combine
import Shared

class RankingViewModelObserver: ObservableObject {
  private let viewModel: RankingViewModel
  private var job: Kotlinx_coroutines_coreJob?
  
  @Published var scores: [Score] = []
  
  init() {
    self.viewModel = RankingViewModel()
    self.job = viewModel.observeScores(onNewScores: { [weak self] scores in
      self?.scores = scores
    })
  }
  
  deinit {
    viewModel.stopObservingScores()
    job?.cancel(cause: nil)
  }
  
  func start() {
    viewModel.startObservingScores()
  }
}
