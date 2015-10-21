class UrlMappings {

	static mappings = {
		"/" {
				controller = 'Oauth'
				action = [GET:'notAllowed', POST:'getOauth', PUT:'notAllowed', DELETE:'notAllowed']
		}
	}
}
