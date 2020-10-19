package com.dog.breed

import android.app.Application
import android.content.Context
import com.dog.breed.constants.DogBreedConstants
import com.dog.breed.constants.DogBreedEnv
import com.dog.breed.managers.SharedPrefManager

class DogBreedApp : Application() {

    companion object {
        var DEBUG = true
        private var authToken: String? = null  // Variable to hold the authentication token of loggedIn user
        private var businessAuthToken: String? = null  // Variable to hold the authentication token of loggedIn user

        /*fun setPreSignedData(context: Context, preSignedData: PreSignedData) {
            Companion.preSignedData = preSignedData

            SharedPrefManager.getInstance(context)
                .setPreference(StackedConstants.PRE_SIGNED_DATA, JsonUtils.toJson(preSignedData))
        }

        fun setUser(context: Context, user: User) {
            Companion.user = user

            SharedPrefManager.getInstance(context)
                .setPreference(StackedConstants.LOGGED_IN_USER_DATA, JsonUtils.toJson(user))
        }

        fun setBusinessUser(context: Context, user: User) {
            Companion.user = user

            SharedPrefManager.getInstance(context)
                .setPreference(StackedConstants.LOGGED_IN_BUSINESS_USER_DATA, JsonUtils.toJson(user))
        }

        fun setBusinessStore(context: Context, store: Stores) {
            Companion.store = store

            SharedPrefManager.getInstance(context)
                .setPreference(StackedConstants.BUSINESS_STORE_DATA, JsonUtils.toJson(store))
        }

        fun setConfigsData(context: Context, configs: Configs) {
            Companion.configs = configs

            SharedPrefManager.getInstance(context)
                .setPreference(StackedConstants.CONFIG_DATA, JsonUtils.toJson(configs))
        }*/

        fun getAuthToken(context: Context): String {
            if (authToken == null) {
                authToken = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.AUTH_TOKEN)
            }
            return authToken!!
        }

        fun clearAll(){
            /*user = null
            store = null*/
            authToken = null
            businessAuthToken = null
        }
//
        /*fun getPreSignedUrl(context: Context): PreSignedData? {
            if (preSignedData == null) {
                val userString = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.PRE_SIGNED_DATA)

                userString?.let {
                    preSignedData = JsonUtils.parseJson<PreSignedData>(it)
                }
            }
            return preSignedData
        }

        fun getUser(context: Context): User? {
            if (user == null) {
                val userString = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.LOGGED_IN_USER_DATA)

                userString?.let {
                    user = JsonUtils.parseJson<User>(it)
                }
            }
            return user
        }

        fun getBusinessUser(context: Context): User? {
            if (user == null) {
                val userString = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.LOGGED_IN_BUSINESS_USER_DATA)

                userString?.let {
                    user = JsonUtils.parseJson<User>(it)
                }
            }
            return user
        }

        fun getBusinessStore(context: Context): Stores? {
            if (store == null) {
                val storeString = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.BUSINESS_STORE_DATA)

                storeString?.let {
                    store = JsonUtils.parseJson<Stores>(it)
                }
            }
            return store
        }

        fun getAuthToken(context: Context): String {
            if (authToken == null) {
                authToken = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.AUTH_TOKEN)
            }
            return authToken!!
        }

        fun getBusinessAuthToken(context: Context): String {
            if (businessAuthToken == null) {
                businessAuthToken = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.BUSINESS_AUTH_TOKEN)
            }
            return businessAuthToken!!
        }

        fun getConfigData(context: Context): Configs {
            if (configs == null) {
                val forceUpdateString = SharedPrefManager.getInstance(context)
                    .getPreferenceDefNull(DogBreedConstants.CONFIG_DATA)

                forceUpdateString?.let {
                    configs = JsonUtils.parseJson<Configs>(it)
                }
            }
            return configs!!
        }*/
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(DogBreedAppRecyclerTracker())
        //FirebaseApp.initializeApp(this)
        if (DogBreedEnv.PROD_MODE.equals(getString(R.string.environment))) {
            DEBUG = false
        } else {
            DEBUG = true
        }




    }





}