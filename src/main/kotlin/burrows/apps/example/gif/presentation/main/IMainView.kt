package burrows.apps.example.gif.presentation.main

import burrows.apps.example.gif.data.rest.model.RiffsyResponse
import burrows.apps.example.gif.presentation.IBaseView
import burrows.apps.example.gif.presentation.adapter.model.ImageInfoModel

/**
 * @author [Jared Burrows](mailto:jaredsburrows@gmail.com)
 */
interface IMainView : IBaseView<IMainPresenter> {
  fun clearImages()
  fun addImages(response: RiffsyResponse?)
  fun showDialog(imageInfoModel: ImageInfoModel?)
  val isActive: Boolean
}
