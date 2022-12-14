package com.vectorinc.task.data.remote.mapper

import com.vectorinc.task.data.local.CharacterEntity
import com.vectorinc.task.data.remote.dto.CharacterDto
import com.vectorinc.task.domain.model.Character


fun CharacterDto.toCharacter(): Character {
    return Character(
        appearance,
        better_call_saul_appearance,
        birthday,
        category,
        char_id,
        img,
        name,
        nickname,
        occupation,
        portrayed,
        status
    )

}

fun CharacterDto.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(img, name)

}


fun CharacterEntity.toCharacter(): Character{
    return Character(
        img = img,
        name = name
    )
}