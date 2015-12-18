class Utils

  def Utils.failsafeArrayHashAppend(hash, key, value)
    if hash.member?(key)
      entry = hash[key]
      entry << value
      hash[key] = entry
    else
      hash[key] = [value]
    end
    return hash
  end
end