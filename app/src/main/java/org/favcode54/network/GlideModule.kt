package org.favcode54.favcode54.network

import com.bumptech.glide.module.AppGlideModule

/**
 * Created by Wilberforce on 1/25/18 at 11:25 PM.
 */
@com.bumptech.glide.annotation.GlideModule
class GlideModule : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}